import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login'
import ManagerHome from '../views/ManagerHome'
import UserHome from '../views/UserHome'
import BusinessHome from "@/views/BusinessHome";

import UserControl from '../../src/components/manager/UserControl'
import GoodControl from "@/components/manager/GoodControl";

import SearchContent from "@/components/usermanage/SearchContent";

import UserInfo from '../../src/components/usermanage/UserInfo.vue'
import UpdateInfo from '../../src/components/usermanage/UpdatePassword.vue'
import Register from "@/views/Register";
import DetailDisplay from "@/components/usermanage/DetailDisplay";
import Cart from "@/components/usermanage/Cart";
import Order from "@/components/usermanage/Order";
import BusinessGood from "@/components/business/BusinessGood";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/manager',
    name: 'ManagerHome',
    component: ManagerHome,
    children:[
        {
          path: '/usercontrol',
          name: 'UserControl',
          component: UserControl
        },
        {
            path: '/goodcontrol',
            name: 'GoodControl',
            component: GoodControl
        },
    ]
  },
    {
      path: '/business',
      name: 'business',
        component: BusinessHome,
        children: [
            {
                path:'/mygood',
                name:'mygood',
                component: BusinessGood
            }

        ]
    },
  {
    path: '/user',
    name: 'UserHome',
    component: UserHome,
    children:[
        {
          path: '/userinfo',
          name: 'userinfo',
          component: UserInfo
        },
        {
          path: '/search',
          name: 'search',
          component: SearchContent
        },
        {
          path: '/detail',
          name: 'detail',
          component: DetailDisplay
        },
        {
          path: '/updateinfo',
          name: 'updatepassword',
          component: UpdateInfo
        },
        {
            path: '/cart',
            name: 'cart',
            component: Cart
        },
        {
            path: '/order',
            name: 'order',
            component: Order
        }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
