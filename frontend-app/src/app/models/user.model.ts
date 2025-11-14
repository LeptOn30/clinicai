export interface DecodedToken {
  sub: string; // Subject (username)
  roles: string;
  iat: number;
  exp: number;
}

export interface User {
  username: string;
  roles: string[];
}