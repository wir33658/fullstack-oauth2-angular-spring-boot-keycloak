# fullstack-oauth2-angular-spring-boot-keycloak
An OAuth2 fullstack example with keycloak, angular and spring boot.

YouTube : https://www.youtube.com/watch?v=DLszg2ul85U

(Info: 
    I created a GitHub-OAuth2-app "my-test-realm" (see: https://github.com/settings/applications/2488878) : ClientID = cebf75f8c1b501b1300e; ClientSecret = CHECK OUT PADLOCK "OAuth-Stuff" 
    I created a Google-OAuth2-app "my-test-realm" (see: https://console.cloud.google.com/apis/credentials/oauthclient/744884992620-9h7f4ek46p3lb8orj967u9gfj76vqmvj.apps.googleusercontent.com?project=my-test-realm-1708802799682) : ClientID = 744884992620-9h7f4ek46p3lb8orj967u9gfj76vqmvj.apps.googleusercontent.com; ClientSecret = CHECK OUT PADLOCK "OAuth-Stuff"
    )

## summary starting things up (terminal)
- Keycloak:
  - cd keycloak
  - docker compose up --build
  - Login Keycloak : https://fuzzy-guacamole-x9vj4qwx763v959-8180.app.github.dev (admin/password)

- Webapp:
  - cd webapp
  - ng serve
  - WebApp : https://fuzzy-guacamole-x9vj4qwx763v959-4200.app.github.dev/ (testuser-1/testpw oder GitHub oder Google Login)

- backend/DemoApplication.java -> Java Debug (to start it)


## setup keycloak

Go to `keycloak` folder, modify `Dockerfile` or `docker-compose.yml` (e.g. adjust the `postgres_data` volume) and start up postgres and keycloak via `docker compose up --build`.

The file `my-test-realm-realm.json` is used to import a complete realm configuration, including clients, users, roles, etc... into keycloak. 

Realm: `my-test-realm`, Username: `testuser-1`, Password: `testuser1`

You may create and configure your own realm by using the keycloak admin console.

Check if the keycloak admin console is reachable (`http://localhost:8180/`).


## angular webapp

Prerequisits : npm install -g @angular/cli
Check        : ng version (if there -> ok)
Then         : ng add angular-oauth2-oidc


Angular webapp is in `webapp`. Made with angular 17.

Using [angular-oauth2-oidc](https://www.npmjs.com/package/angular-oauth2-oidc)!

The `main.ts` file bootstraps the webapp by proving the http client and the oauthservice. Also initializing the oauthservice by providing a configuration, setup of silent token refresh, loading discovery document and login of user, if not already done.

The component `AppComponent` provides a basic demo of logout and calling a protected API with the access token.

## spring-boot backend

Spring boot backend is in `backend` folder. Requires Maven and Java 21.

The class `SecurityConfig` configures the security filter chain, enabling CORS, makes sure that all requests must be authenticated, configures to be an oauth2 resource server (verify access token via JWT issuer) and to use a custom JWT converter to extract all relevant data from the JWT.

The `application.properties` file has the JWT issuer configured, pointing to the locally running keycloak.

The `CustomJwt` is a customized JWT containing all relevant information we need extracted from the JWT bearer token.

The `HelloController` has a basic GET endpoint, CORS is configured to work with a locally running angular webapp. The GET method returns a message, but only for authorized users which have the authority `ROLE_fullstack-developer`.

The granted authorities are extracted by the `CustomJwtConverter`.


