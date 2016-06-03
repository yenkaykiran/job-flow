(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .controller('JobStepDeleteController',JobStepDeleteController);

    JobStepDeleteController.$inject = ['$uibModalInstance', 'entity', 'JobStep'];

    function JobStepDeleteController($uibModalInstance, entity, JobStep) {
        var vm = this;
        vm.jobStep = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            JobStep.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
