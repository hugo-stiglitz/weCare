'use strict';

angular.module('weCare', ['ngMaterial', 'ngAnimate', 'ngAria', 'ui.bootstrap'])

    .config(function ($mdThemingProvider) {
        $mdThemingProvider.theme('default')
            .primaryPalette('red')
            .accentPalette('amber');
    });