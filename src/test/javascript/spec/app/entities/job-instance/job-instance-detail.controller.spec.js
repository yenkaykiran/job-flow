'use strict';

describe('Controller Tests', function() {

    describe('JobInstance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockJobInstance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockJobInstance = jasmine.createSpy('MockJobInstance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'JobInstance': MockJobInstance
            };
            createController = function() {
                $injector.get('$controller')("JobInstanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jobflowApp:jobInstanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
