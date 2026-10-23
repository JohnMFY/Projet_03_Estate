import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { LoginRequest } from '../interfaces/loginRequest.interface';
import { AuthSuccess } from '../interfaces/authSuccess.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { User } from 'src/app/interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'api/auth';

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(
      `${this.pathService}/register`,
      registerRequest
    );
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {

    const body = {
      query: `
        mutation Login($input: LoginInput!) {
          login(input: $input) {
            token
          }
        }
      `,
      variables: {
        input: {
          email: loginRequest.email,
          password: loginRequest.password
        }
      }
    };

    return this.httpClient.post<any>('/graphql', body).pipe(
      map(response => ({
        token: response.data.login.token
      }))
    );
  }

  public me(): Observable<User> {

    const body = {
      query: `
        query Me {
          me {
            id
            name
            email
            created_at
            updated_at
          }
        }
      `
    };

    return this.httpClient.post<any>('/graphql', body).pipe(
      map(response => response.data.me)
    );
  }
}