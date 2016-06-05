(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .controller('JobStepDialogController', JobStepDialogController);

    JobStepDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JobStep', 'JobInstance'];

    function JobStepDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, JobStep, JobInstance) {
        var vm = this;
        vm.jobStep = entity;
        vm.jobinstances = JobInstance.query();
        if(vm.jobStep.jobInstanceId) {
            vm.jobsteps = vm.getInstanceSteps(vm.jobStep.jobInstanceId);
        } else {
            vm.jobsteps = JobStep.query();
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('jobflowApp:jobStepUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.jobStep.id !== null) {
                JobStep.update(vm.jobStep, onSaveSuccess, onSaveError);
            } else {
                JobStep.save(vm.jobStep, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        
        vm.getInstanceSteps = function(instanceId) {
            return JobStep.byInstance({instance: instanceId});
        }
    }
})();
