'use strict';

describe('Controller Tests', function() {

    describe('JobStep Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockJobStep, MockJobInstance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockJobStep = jasmine.createSpy('MockJobStep');
            MockJobInstance = jasmine.createSpy('MockJobInstance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'JobStep': MockJobStep,
                'JobInstance': MockJobInstance
            };
            createController = function() {
                $injector.get('$controller')("JobStepDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jobflowApp:jobStepUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
