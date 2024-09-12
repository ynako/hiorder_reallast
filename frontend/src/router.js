
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import OrderOrderManager from "./components/listers/OrderOrderCards"
import OrderOrderDetail from "./components/listers/OrderOrderDetail"

import MenuMenuManager from "./components/listers/MenuMenuCards"
import MenuMenuDetail from "./components/listers/MenuMenuDetail"


import OrderManagementOrdermenuManager from "./components/listers/OrderManagementOrdermenuCards"
import OrderManagementOrdermenuDetail from "./components/listers/OrderManagementOrdermenuDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/orders/orders',
                name: 'OrderOrderManager',
                component: OrderOrderManager
            },
            {
                path: '/orders/orders/:id',
                name: 'OrderOrderDetail',
                component: OrderOrderDetail
            },

            {
                path: '/menus/menus',
                name: 'MenuMenuManager',
                component: MenuMenuManager
            },
            {
                path: '/menus/menus/:id',
                name: 'MenuMenuDetail',
                component: MenuMenuDetail
            },


            {
                path: '/orderManagements/ordermenus',
                name: 'OrderManagementOrdermenuManager',
                component: OrderManagementOrdermenuManager
            },
            {
                path: '/orderManagements/ordermenus/:id',
                name: 'OrderManagementOrdermenuDetail',
                component: OrderManagementOrdermenuDetail
            },



    ]
})
