// This file is auto-generated by @hey-api/openapi-ts

import { createClient, createConfig } from "@hey-api/client-axios";

// default configuration, the client instance is http://localhost:8080/
// export const client = createClient(createConfig());

// my client configuration below

export const client = createClient({
  ...createConfig(),
  baseURL: "http://localhost:8121/",
  withCredentials: true,
});
