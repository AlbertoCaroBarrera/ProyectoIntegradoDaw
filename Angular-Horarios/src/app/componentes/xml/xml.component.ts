import { Component } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { PrimengModule } from '../../primeng.module';
import { FileUploadHandlerEvent } from 'primeng/fileupload';
import { XmlService } from '../../services/xml/xml.service';

@Component({
  selector: 'app-xml',
  standalone: true,
  imports: [PrimengModule],
  templateUrl: './xml.component.html',
  styleUrls: ['./xml.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class XMLComponent {
  uploadedFiles: any[] = [];
  progressVisible: boolean = false;

  constructor(
    private xmlService: XmlService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  onUpload(event: FileUploadHandlerEvent) {
    console.log('onUpload', event);
    if (event.files && event.files.length > 0) {
      const formData = new FormData();
      formData.append('file', event.files[0]);

      this.progressVisible = true; 

      this.xmlService.uploadXml(formData).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'XML uploaded successfully' });
        },
        error: () => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to upload XML', life: 3000 });
        }
      });
    }
  }



  resetDatabase() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete all databases?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.xmlService.resetDatabase().subscribe({
          next: response => {
            this.messageService.add({ severity: 'success', summary: 'Success', detail: response, life: 5000 });
          },
          error: error => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to reset database', life: 3000 });
          }
        });
      }
    });
  }
}
