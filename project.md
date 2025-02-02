# Project Implementation Note

## project requirement(frontend):

### software and frameworks

1. Node.js (v18.20.6 LTS)
2. npm (10.2.8)

## project initialization:

Vue-CLI: A scaffolding system for rapid Vue project development

## introduction of components

## global state management

### what is vuex?

vuex: https://vuex.vuejs.org/
Vuex is a state management pattern + library for Vue.js applications.

![vuex](./img/vuex.png "vuex workflows")

### why we need vuex?

we can use the vuex to make the user information global and accessible to all the component in the application.

Basically, VUEX provides us APIs for CRUD operation on global variables(state information), and also some other features(such as time travel)

- states: the state information, such as user information
- mutations: similar to events, each mutation has a string type and a handler, When a mutation with type `increment` is triggered, call its handler.

Get the state of variable:

```tsx
const store = useStore();
console.log(store.state.user?.loginUser);
```

- actions: similar to mutations, but instead of mutating state, actions commit mutations, Actions can contain arbitrary asynchronous operations.

Commit a mutation:

```tsx
setTimeout(() => {
  store.dispatch("user/getLoginUser", {
    userName: "wzh",
  });
}, 3000);
```

## Permission Management

### Purpose:

use a generalized mechanism to define the need of permission for accessing a page.

#### mechanism:

1. define the accessing authority of a specific route in the `routes.ts` file.
2. For the `components`, we define a global router listener. Each time when accessing a new page, check first according to the next route information, whether the user has the authority to access the current page.
3. If the user has the authority, jumps directly to the page, otherwise, intercept the access and goes to `401` page or login page.

## Layout Optimization (29.01.2025)

1. the page footer is not fixed at the bottom when scaling the browser:

![footNotFixed](./img/footerNotFixed.PNG "Bug Footer Not Fixed")

Solution: change the position property from `absolute` to `sticky`. In the meanwhile, restrict the height of the layout

```css
#basicLayout .footer {
  position: sticky;
}
```

```html
<a-layout style="min-height: 100vh" \></a-layout>
```

2. Content style, GlobalHeader style optimization.
3. globalHeader wrap problem solved by adding `:wrap="false"`.

## General navigation bar component

### task 1

To hide unnesseray content to unauthorized user, this part is additional part to general component implementation.

#### solution

1. add a flag variable `hideInMenu` to the routes indicating that whether to show the route to the user or not.

```tsx
{
  path: "/hidden",
  name: "hidden",
  component: HomeView,
  meta: {
    hideInMenu: true,
  },
},
```

2. filter out all unnesseray routes according to the flag, and then show them on browser.

```tsx
const visibleRoutes = routes.filter((item, index) => {
  if (item.meta?.hideInMenu) {
    return false;
  }
  return true;
});
```

## Global Permission Management

### purpose

Based on requirement, we only show the menu items according to the user authority. If the user does not have the permission to access a page, we will not show them on the navigation bar.

So we need a global-wise enumeration variable for denoting the access permission level.

### define the enum variable

1. create a directory called `./src/access`
2. add a typescript file with:

```tsx
const ACCESS_ENUM = {
  NOT_LOGIN: "notLogin",
  USER: "user",
  ADMIN: "admin",
};

export default ACCESS_ENUM;
```

### extract the authority check in a module

create the `checkAccess.ts` file.

```tsx
const checkAccess = (loginUser: any, needAccess = ACCESS_ENUM.NOT_LOGIN) => {
  // get the login type of the current user, if there are no loginUser, get NOT_LOGIN by default.
  const loginUserAccess = loginUser?.userRole ?? ACCESS_ENUM.NOT_LOGIN;

  if (needAccess === ACCESS_ENUM.NOT_LOGIN) {
    return true;
  }
  // if the user need to login first.
  if (needAccess === ACCESS_ENUM.USER) {
    if (loginUser === ACCESS_ENUM.NOT_LOGIN) {
      return false;
    }
  }
  // if the user need the admin permission
  if (needAccess === ACCESS_ENUM.ADMIN) {
    //if the user does not have the admin permission
    if (loginUser !== ACCESS_ENUM.ADMIN) {
      return false;
    }
  }

  return true;
};
export default checkAccess;
```

### dynamically update navigation menu according to state changes

Modify the dynamic menu `GlobalHeader` to ensure filtering out unnesseray pages according to the login state changes. Using the `computed()` function. When the user state is changed, the rerendering of navigation menu is triggered.

```tsx
const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    if (
      !checkAccess(store.state.user.loginUser, item?.meta?.access as string)
    ) {
      return false;
    }
    return true;
  });
});
```

### Global initialization, only called when initializing project

```tsx
const doInit = () => {
  // todo: to add some initialization code here
  console.log("hello, welcome to OJ system!");
};

onMounted(() => {
  doInit();
});
```

## Backend(30.01.2025)

## Backend Framework Initialization

We found a general spring-boot based backend project template from github:
[SpringBoot Project Initialization Template for Backend Project: https://github.com/LURENYUANSHI/springboot-init-master/tree/master](https://github.com/LURENYUANSHI/springboot-init-master/tree/masterhttps://www.genome.gov/)

### The architecture of the springboot-init template

- `\doc`: for documentation
- `\sql`: for sql files(`create_table.sql` initialization script for database)
  - user table
  - post table
  - post likes table `post_thumb`: which user liked which post
  - post added to favorite table `post_favour`: who had added the post to his favorite list
  - `post_es_mapping.json`: the mapping file to create the database in Elasticsearch.
- `src\....\annotation`: for annotation files
- `src\....\aop`: files for AOP, Aspect-oriented programming, for example the global authority check, the global log updating, adding new feature to existing classes without changing the original code.
- `src\....\common`: some common templates, for example generalized `basicResponse` and `deleteRequest`
- `src\....\config`: configuration files, for loading the config content defined in `application.yml`, for initializing some setting in Client side.
- `src\....\constant`: definition of const variables.
- `src\....\controller`: receive request
- `src\....\esdao`: similar to the `mapper` in `mybatis`, for manipulating ES.
- `src\....\exception`: global handler for exceptions.
- `src\....\job`: task related, scheduled task, one-time task, recurring task.
- `src\....\manager`: service layer(usually we put the definition of common service here, for instance, third-party API).
- `src\....\mapper`: data accessing layer of `mybatis`, for managing databases.
- `src\....\model`: data model for handling the data, entity classes, wrapper classes, enum types.
- `src\....\service`: service layer, for business logic.
- `src\....\utils`: utility classes, static methods.
- `src\test`: unit testing,
- `MainApplication`: Entrance of the application
- `Dockerfile`: for building docker images.

## Front-End and Back-End Integration

> how can they be integrated?

**API / Request**, the frontend sends requests to call the API from backend, solution: `Axios`

### Install Axios

> what is axios?

Axios is Promise based HTTP client for the browser and node.js

Documentation: [https://axios-http.com/docs/intro](https://axios-http.com/docs/intro)

```sh
npm install axios
```

### Write the code to call back-end!(31.01.2025)

Traditionally, we need to write the code for each request individually. At least a request path.

But there are some automatic ways for doing this:
[https://github.com/ferdikoomen/openapi-typescript-codegen](https://github.com/ferdikoomen/openapi-typescript-codegen)

> Updated Usage Information: Currently the project `ferdikoomen/openapi-typescript-codegen` has been unmaintained for a while, the alternative can be `@hey-api/openapi-ts`.

```sh
# using openapi-typescript-codegen
npm install openapi-typescript-codegen --save-dev

# usage examples:

openapi --input ./example.json --output ./directory --client axios

#using openapi-ts
npm install @hey-api/client-fetch && npm install @hey-api/openapi-ts -D

#quick use of openapi-ts
npx @hey-api/openapi-ts \
  -i path/to/openapi.json \
  -o src/client \
  -c @hey-api/client-fetch
```

Project openapi-ts: [https://github.com/hey-api/openapi-ts](https://github.com/ferdikoomen/openapi-typescript-codegen)

#### generate the client api tsx code.

```sh
npx @hey-api/openapi-ts -i http://localhost:8121/api/v2/api-docs -o ./generated -c @hey-api/client-axios
```

the HTTP client for this project is based on Axios, so the client is set to `client-axios`

#### configuration of client

There are two types of way for configuring the request.

1. Configure the generated API:

In `client.gen.ts`, we can specify our client configuration. we need to specify the server url for using the generated API, the client url is http://localhost:8080/ by default

```tsx
export const client = createClient({
  ...createConfig(),
  baseURL: "http://localhost:8121/",
});
```

2. Modify the global variables of Axios using a interceptors

Doc: [https://axios-http.com/docs/interceptors](https://axios-http.com/docs/interceptors)

#### Sent request to server using API, for example, get the user login information

```tsx
actions: {
  async getLoginUser({ commit, state }, payload) {
    // todo remote login
    const res = await getLoginUserUsingGet();
    if (res.status === 200) {
      commit("updateUser", res.data);
    } else {
      commit("updateUser", {
        ...state.loginUser,
        userRole: ACCESS_ENUM.NOT_LOGIN,
      });
    }
    console.log(res.status);
  },
},
```

## User Login Implementation

### Auto Sign-In

- Task 1: Get the user remote login information

For good maintainability, we write code in `store/user.ts`

```tsx
actions: {
    async getLoginUser({ commit, state }, payload) {
      // todo remote login
      const res = await getLoginUserUsingGet();
      if (res.data?.code === 0) {
        commit("updateUser", res.data.data);
      } else {
        commit("updateUser", {
          ...state.loginUser,
          userRole: ACCESS_ENUM.NOT_LOGIN,
        });
      }
    },
  },
```

- task 2: How to trigger the `getLoginUser()`?

we should make it globally, since we run this piece of code for once, and we need to access the result more times in many use cases.

Solutions:

1. route interceptions
2. application entrance `App.vue`
3. make this function a common global component

### Global Permission Management Optimization (02.02.2025)

1. create a new file "access/index.ts"

put all the access checking logic into this file as well as the auto login check

```tsx
router.beforeEach(async (to, from, next) => {
  // get loginUser state information
  const loginUser = store.state.user.loginUser;

  // if the user hasn't logged in before, auto login
  if (!loginUser || !loginUser.userRole) {
    // get the user state information after the login process done.
    await store.dispatch("user/getLoginUser");
  }

  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN;

  // when jumps to a page that requires login.
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    // if the user hasn't logged in, jumps to login page.
    if (!loginUser || !loginUser.userRole) {
      next("/user/login?redirect=${to.path}");
      return;
    }
    // already logged in, but need admin authority level
    if (!checkAccess(loginUser, needAccess)) {
      next("/noAuth");
      return;
    }
  }
  next();
});
```

### Interceptors for configuring the request and response

As we are using the `@hey-api/client-axios` for HTTP requests and responses handling, we are able to modify the requests and responses via the interceptors. See [https://heyapi.dev/openapi-ts/clients/axios](https://heyapi.dev/openapi-ts/clients/axios)

1. create a directory `./plugins`, let's put all the customized plugin files into this directory.
2. create a script `./plugins/axios.ts` and cat the following configuration at the end of this file.

```tsx
import { client } from "../../generated/client.gen";

client.instance.interceptors.request.use(
  // do something
  function (config) {
    // Do something before request is sent
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
client.instance.interceptors.response.use(
  function (response) {
    console.log("response:", response);
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  },
  function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  }
);
```

3. And import `plugins` in `main.ts`, Now we are able to customized our requests and responses. for instance, we can print out the response by:

```tsx
client.instance.interceptors.response.use(function (response) {
  console.log("response:", response);
  // Any status code that lie within the range of 2xx cause this function to trigger
  // Do something with response data
  return response;
});
```

### Optimization of supporting multiple layouts

1. add some new routes in `router/routes.ts`

```tsx
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
  },
];
```

2. create new pages: `UserLayout`,`UserLoginView` and `UserRegisterView`
3. Adding logic for generating pages according to the route with different layouts in `App.vue`.

```tsx
<div id="app">
  <template v-if="route.path.startsWith('/user')">
    <router-view />
  </template>
  <template v-else>
    <BasicLayout />
  </template>
</div>
```

### User login page implementation

1. create the route for user login page.
