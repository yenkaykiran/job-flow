(function() {
    'use strict';

    angular
        .module('jobflowApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('job-instance', {
            parent: 'entity',
            url: '/job-instance?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobInstances'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-instance/job-instances.html',
                    controller: 'JobInstanceController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('job-instance-detail', {
            parent: 'entity',
            url: '/job-instance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobInstance'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-instance/job-instance-detail.html',
                    controller: 'JobInstanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'JobInstance', function($stateParams, JobInstance) {
                    return JobInstance.get({id : $stateParams.id});
                }]
            }
        })
        .state('job-instance.new', {
            parent: 'job-instance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-instance/job-instance-dialog.html',
                    controller: 'JobInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                enabled: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('job-instance', null, { reload: true });
                }, function() {
                    $state.go('job-instance');
                });
            }]
        })
        .state('job-instance.edit', {
            parent: 'job-instance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-instance/job-instance-dialog.html',
                    controller: 'JobInstanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobInstance', function(JobInstance) {
                            return JobInstance.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-instance', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-instance.delete', {
            parent: 'job-instance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-instance/job-instance-delete-dialog.html',
                    controller: 'JobInstanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JobInstance', function(JobInstance) {
                            return JobInstance.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-instance', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        }).state('job-instance.newStep', {
            parent: 'job-instance',
            url: '/{id}/newStep',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'JobInstance', function($stateParams, $state, $uibModal, JobInstance) {
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
                                id: null,
                                jobInstance: JobInstance.get({id : $stateParams.id}),
                                disableJobInstance: true
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('job-instance', null, { reload: true });
                }, function() {
                    $state.go('job-instance');
                });
            }]
        }).state('job-instance.steps', {
            parent: 'job-instance',
            url: '/{id}/steps',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Instance Steps'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-step/job-steps.html',
                    controller: 'JobStepController1',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        }).state('job-instance.chart', {
            parent: 'job-instance',
            url: '/{id}/chart',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Instance Chart'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-instance/job-chart.html',
                    controller: 'JobChartController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }

})();
