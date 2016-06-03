(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .controller('JobInstanceDialogController', JobInstanceDialogController);

    JobInstanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'JobInstance'];

    function JobInstanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, JobInstance) {
        var vm = this;
        vm.jobInstance = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('jobflowApp:jobInstanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.jobInstance.id !== null) {
                JobInstance.update(vm.jobInstance, onSaveSuccess, onSaveError);
            } else {
                JobInstance.save(vm.jobInstance, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
