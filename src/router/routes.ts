import { RouteRecordRaw } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import AdminView from "@/views/AdminView.vue";
import NoAuthView from "@/views/NoAuthView.vue";
import ACCESS_ENUM from "@/access/accessEnum";
import UserLoginView from "@/views/user/UserLoginView.vue";
import UserRegisterView from "@/views/user/UserRegisterView.vue";
import UserLayout from "@/layouts/UserLayout.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/user",
    name: "User",
    component: UserLayout,
    children: [
      {
        path: "/user/login",
        name: "User Login",
        component: UserLoginView,
      },
      {
        path: "/user/register",
        name: "User Register",
        component: UserRegisterView,
      },
    ],
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/",
    name: "Problems",
    component: HomeView,
  },
  {
    path: "/hidden",
    name: "Hidden",
    component: HomeView,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/admin",
    name: "Administrator",
    component: AdminView,
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/noAuth",
    name: "NoAuthority",
    component: NoAuthView,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
];
