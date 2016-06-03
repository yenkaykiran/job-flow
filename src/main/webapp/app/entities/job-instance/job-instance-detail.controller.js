(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .controller('JobInstanceDetailController', JobInstanceDetailController);

    JobInstanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'JobInstance'];

    function JobInstanceDetailController($scope, $rootScope, $stateParams, entity, JobInstance) {
        var vm = this;
        vm.jobInstance = entity;
        
        var unsubscribe = $rootScope.$on('jobflowApp:jobInstanceUpdate', function(event, result) {
            vm.jobInstance = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
