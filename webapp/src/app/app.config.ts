import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { AuthConfig, OAuthService, provideOAuthClient } from 'angular-oauth2-oidc';
import { provideHttpClient } from '@angular/common/http';

export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8180/realms/my-test-realm',
  tokenEndpoint: 'http://localhost:8180/realms/my-test-realm/protocol/openid-connect/token',
  redirectUri: window.location.origin,
//  issuer: 'https://fuzzy-guacamole-x9vj4qwx763v959-8180.app.github.dev/realms/my-test-realm',
//  tokenEndpoint: 'https://fuzzy-guacamole-x9vj4qwx763v959-8180.app.github.dev/realms/my-test-realm/protocol/openid-connect/token',
//  redirectUri: 'https://fuzzy-guacamole-x9vj4qwx763v959-4200.app.github.dev',
  clientId: 'my-webapp-client',
  responseType: 'code',
  scope: 'openid profile',
  showDebugInformation: true,
};

function initializeOAuth(oauthService: OAuthService): Promise<void> {
  return new Promise((resolve) => {
    oauthService.configure(authCodeFlowConfig);
    oauthService.setupAutomaticSilentRefresh();
    oauthService.loadDiscoveryDocumentAndLogin()
      .then(() => resolve());
  });
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideOAuthClient(),
    {
      provide: APP_INITIALIZER,
      useFactory: (oauthService: OAuthService) => {
        return () => {
          initializeOAuth(oauthService);
        }
      },
      multi: true,
      deps: [
        OAuthService
      ]
    }
  ]
};
