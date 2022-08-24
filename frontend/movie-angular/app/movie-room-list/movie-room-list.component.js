'use strict';

// Register `phoneList` component, along with its associated controller and template
angular.module('movieRoomList').component('movieRoomList', {
    templateUrl: 'movie-room-list/movie-room-list.template.html',
    controller: ['MovieRoom', '$routeParams',
        function MovieRoomListController(MovieRoom, $routeParams) {
            var self = this;

            self.cinemaId = $routeParams.cinemaId;

            MovieRoom.getData().then(function (data) {
                    self.movieRooms = data;
                }
            );
        }
    ]
});
