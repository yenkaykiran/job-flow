(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('job-step', {
            parent: 'entity',
            url: '/job-step',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobSteps'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-step/job-steps.html',
                    controller: 'JobStepController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('job-step-detail', {
            parent: 'entity',
            url: '/job-step/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobStep'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-step/job-step-detail.html',
                    controller: 'JobStepDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'JobStep', function($stateParams, JobStep) {
                    return JobStep.get({id : $stateParams.id});
                }]
            }
        })
        .state('job-step.new', {
            parent: 'job-step',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-step/job-step-dialog.html',
                    controller: 'JobStepDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                stepType: null,
                                stepStatus: null,
                                message: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('job-step', null, { reload: true });
                }, function() {
                    $state.go('job-step');
                });
            }]
        })
        .state('job-step.edit', {
            parent: 'job-step',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-step/job-step-dialog.html',
                    controller: 'JobStepDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobStep', function(JobStep) {
                            return JobStep.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-step', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-step.delete', {
            parent: 'job-step',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-step/job-step-delete-dialog.html',
                    controller: 'JobStepDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JobStep', function(JobStep) {
                            return JobStep.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-step', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
