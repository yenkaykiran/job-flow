(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .controller('JobInstanceDeleteController',JobInstanceDeleteController);

    JobInstanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobInstance'];

    function JobInstanceDeleteController($uibModalInstance, entity, JobInstance) {
        var vm = this;
        vm.jobInstance = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            JobInstance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
