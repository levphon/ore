/* global angular */
(function(angular) {
    'use strict';
    angular
        .module('datatables')
        .run(function(DTDefaultOptions) {
            DTDefaultOptions.setLanguageSource('js/plugins/dataTables/language.json')
            DTDefaultOptions.setOption('bFilter', false) // 禁用搜索框
            DTDefaultOptions.setOption('lengthChange', false) // 禁用动态显示行数;
            DTDefaultOptions.setOption('full_numbers')
            DTDefaultOptions.setOption('autoWidth', true);
        });
})(angular);
