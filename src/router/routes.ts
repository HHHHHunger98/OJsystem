import { RouteRecordRaw } from "vue-router";
import HomeView from "../views/HomeView.vue";
import AdminView from "@/views/AdminView.vue";
import NoAuthView from "../views/NoAuthView.vue";
import ACCESS_ENUM from "@/access/accessEnum";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Problems",
    component: HomeView,
  },
  {
    path: "/hidden",
    name: "hidden",
    component: HomeView,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/admin",
    name: "administrator",
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
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
];
