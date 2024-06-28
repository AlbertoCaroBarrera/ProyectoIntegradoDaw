import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../../clases/user';

@Injectable({
  providedIn: 'root'
})
export class UserStateService {
  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  constructor() {
    this.userSubject = new BehaviorSubject<User | null>(null);
    this.user = this.userSubject.asObservable();
  }

  public get currentUser(): User | null {
    return this.userSubject.value;
  }

  updateUser(user: User): void {
    this.userSubject.next(user);
  }

  clearUser(): void {
    this.userSubject.next(null);
  }
}
