'use strict';

// Register `phoneList` component, along with its associated controller and template
angular.module('cinemaList').component('cinemaList', {
    templateUrl: 'cinema-list/cinema-list.template.html',
    controller: ['Cinema',
        function CinemaListController(Cinema) {
            var self = this;

            Cinema.getData().then(function (data) {
                    self.cinemas = data;
                }
            );
        }
    ]
});
