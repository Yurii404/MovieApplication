'use strict';

// Register `phoneList` component, along with its associated controller and template
angular.module('movieSessionList').component('movieSessionList', {
    templateUrl: 'movie-session-list/movie-session-list.template.html',
    controller: ['MovieSession', '$routeParams',
        function MovieSessionListController(MovieSession, $routeParams) {
            var self = this;

            self.roomId = $routeParams.roomId;

            MovieSession.getSessionList().then(function (data) {
                    self.movieSessions = data;
                }
            );

            self.toPretty = function (dateTime){
                var prettyDateTime = '';

                prettyDateTime = prettyDateTime + dateTime.substring(0, 10);
                prettyDateTime = prettyDateTime + ' | ' + dateTime.substring(11, 16);

                return prettyDateTime;
            };

        }
    ]
});
