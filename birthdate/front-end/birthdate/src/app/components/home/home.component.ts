import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserInfo } from 'src/app/models/user-info.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public showInfo = false
  public loading = false
  public userInfo = new UserInfo()
  public form: FormGroup
  private url = 'http://localhost:8080/api/v1/birthdate'

  constructor(
    private http: HttpClient,
    private fb: FormBuilder,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.initForm()
  }

  private initForm(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      birthdate: ['', [Validators.required]]
    })
  }

  public submit(): void {
    if (this.form.valid) {
      this.loading = true
      this.post(this.form.value)
        .subscribe(
          (res: UserInfo) => this.renderInf(res),
          err => {
            this.loading = false
            this._snackBar.open(
              'Hubo un error de conección con nuestro servicio',
              'OK',
              {
                duration: 3600,
                verticalPosition: 'bottom',
                horizontalPosition: 'center'
              }
            ); console.log(err)
          }
        )
    }
  }

  public reset(): void {
    this.userInfo = null
    this.showInfo = false
    this.form.reset()
  }

  private renderInf(userInfo: UserInfo): void {
    this.userInfo = userInfo
    this.showInfo = true
    this.loading = false
  }

  private post(data: any): Observable<object> {
    return this.http.post<UserInfo>(this.url, data);
  }
}
