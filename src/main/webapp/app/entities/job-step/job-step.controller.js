(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .controller('JobStepController', JobStepController);

    JobStepController.$inject = ['$scope', '$state', 'JobStep', 'ParseLinks', 'AlertService'];

    function JobStepController ($scope, $state, JobStep, ParseLinks, AlertService) {
        var vm = this;
        vm.jobSteps = [];
        vm.predicate = 'id';
        vm.reverse = true;
        vm.page = 0;
        vm.loadAll = function() {
            JobStep.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.jobSteps.push(data[i]);
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        };
        vm.reset = function() {
            vm.page = 0;
            vm.jobSteps = [];
            vm.loadAll();
        };
        vm.loadPage = function(page) {
            vm.page = page;
            vm.loadAll();
        };

        vm.loadAll();

    }
    
    angular
    .module('jobflowApp')
    .controller('JobStepController1', JobStepController1);

    JobStepController1.$inject = ['$scope', '$state', 'JobStep', 'ParseLinks', 'AlertService', '$stateParams'];

    function JobStepController1 ($scope, $state, JobStep, ParseLinks, AlertService, $stateParams) {
        var vm = this;
        vm.jobSteps = [];
        vm.reverse = true;
        vm.loadAll = function() {
            JobStep.query({
                instance: $stateParams.id
            }, onSuccess);
            function onSuccess(data, headers) {
                for (var i = 0; i < data.length; i++) {
                    vm.jobSteps.push(data[i]);
                }
            }
        };

        vm.loadAll();
    }
    
    angular
    .module('jobflowApp')
    .controller('JobChartController', JobChartController);

    JobChartController.$inject = ['$scope', '$state', 'JobStep', 'ParseLinks', 'AlertService', '$stateParams'];

    function JobChartController ($scope, $state, JobStep, ParseLinks, AlertService, $stateParams) {
        var vm = this;
        vm.jobSteps = [];
        vm.reverse = true;
        vm.generate = function() {
            JobStep.query({
                instance: $stateParams.id
            }, onSuccess);
            function onSuccess(data, headers) {
                for (var i = 0; i < data.length; i++) {
                    vm.jobSteps.push(data[i]);
                }
                
                var chartString = "";
                for(var i=0;i<vm.jobSteps.length;i++) {
                    var step = vm.jobSteps[i];
                    chartString += i + '=>' + step.stepType.toLowerCase() + ": " + step.name + "|" + step.stepStatus.toLowerCase() + "\n";
                }
                chartString += '\n';
                for(var i=0;i<vm.jobSteps.length;i++) {
                    var step = vm.jobSteps[i];
                    var path = generatePath(step);
                    if(path.indexOf('->') >= 0) {
                        chartString += path +'\n';
                    }
                    var conds = generateConditions(step);
                    if(conds.indexOf('->') >= 0) {
                        chartString += conds +'\n';
                    }
                }
                
                vm.chartData = chartString;
                var diagram = flowchart.parse(vm.chartData);
                diagram.drawSVG('diagram');
            }
            
            function generatePath(step) {
                var path = '';
                if(step) {
                    path += findIndex(step);
                    if(step.nextStepId) {
                        path += "->";
                    }
                    path += generatePath(findStep(step.nextStepId));
                    console.log(path);
                }
                return path;
            }
            
            function generateConditions(step) {
                var path = '';
                if(step) {
                    if(step.yesPathId) {
                        path += findIndex(step);
                        path += "(yes)->";
                        path += generatePath(findStep(step.yesPathId));
                    }
                    path += '\n';
                    if(step.noPathId) {
                        path += findIndex(step);
                        path += "(no)->";
                        path += generatePath(findStep(step.noPathId));
                    }
                    console.log(path);
                }
                return path;
            }
            
            function findIndex(step) {
                var index = 0;
                for(var i=0;i<vm.jobSteps.length;i++) {
                    if(vm.jobSteps[i].id == step.id) {
                        index=i;
                        break;
                    }
                }
                return index;
            }
            
            function findStep(stepId) {
                var step = null;
                for(var i=0;i<vm.jobSteps.length;i++) {
                    if(vm.jobSteps[i].id == stepId) {
                        step = vm.jobSteps[i];
                        break;
                    }
                }
                return step;
            }
            
        };
        
        vm.generate();

    }
})();
