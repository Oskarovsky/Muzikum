import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../services/auth/token-storage.service';
import { Observable } from 'rxjs';
import {UploadFileService} from '../services/storage/upload-file.service';
import { HttpEventType, HttpResponse, HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  selectedFile;
  currentFile: File;
  message = '';
  userAvatar: any;

  fileInfos: Observable<any>;

  constructor(private token: TokenStorageService,
              private uploadService: UploadFileService,
              private httpClient: HttpClient) { }

  ngOnInit() {
    this.currentUser = this.token.getUser();
    this.fileInfos = this.uploadService.getFile(this.currentUser.username);
  }

  selectFile(event) {
    this.selectedFile = event.target.files;
  }

  upload() {
    this.currentFile = this.selectedFile.item(0);
    this.uploadService.upload(this.currentFile, this.currentUser.username).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
          // this.fileInfos = this.uploadService.getFile(this.currentUser.username);
        }
      },
      err => {
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });

    this.selectedFile = undefined;
  }




}
