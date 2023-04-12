import {Component} from '@angular/core';
import {ChangelogService} from '../services/changelog.service';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-changelog-create',
  templateUrl: './changelog-create.component.html',
  styleUrls: ['./changelog-create.component.scss']
})
export class ChangelogCreateComponent {

  createForm: FormGroup;

  constructor(private fb: FormBuilder, private changelogService: ChangelogService) {
    this.createForm = this.fb.group({
      version: ['', Validators.required],
      releaseDate: ['', Validators.required],
      changes: this.fb.array([])
    });
  }

  get changes(): FormArray {
    return this.createForm.get('changes') as FormArray;
  }

  addChange() {
    this.changes.push(this.fb.group({
      type: ['', Validators.required],
      description: ['', Validators.required],
      usNumber: ['']
    }));
  }

  removeChange(index: number) {
    this.changes.removeAt(index);
  }

  onSubmit() {
    if (this.createForm.valid) {
      this.changelogService.addVersion(this.createForm.value).subscribe(response => {
        // Handle success
        console.log('Version added:', response);
        window.location.reload();
      }, error => {
        // Handle error
        console.error('Error adding version:', error);
      });
    }
  }
}
