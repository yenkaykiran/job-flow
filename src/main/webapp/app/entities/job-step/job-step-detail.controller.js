(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .controller('JobStepDetailController', JobStepDetailController);

    JobStepDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'JobStep', 'JobInstance'];

    function JobStepDetailController($scope, $rootScope, $stateParams, entity, JobStep, JobInstance) {
        var vm = this;
        vm.jobStep = entity;
        
        var unsubscribe = $rootScope.$on('jobflowApp:jobStepUpdate', function(event, result) {
            vm.jobStep = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
