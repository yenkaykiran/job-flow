(function() {
    'use strict';
    angular
        .module('jobflowApp')
        .factory('JobStep', JobStep);

    JobStep.$inject = ['$resource'];

    function JobStep ($resource) {
        var resourceUrl =  'api/job-steps/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' },
            'byInstance': { method: 'GET', isArray: true}
        });
    }
})();
