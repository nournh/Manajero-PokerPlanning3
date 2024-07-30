import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProjectService } from '../../core/Sevice/project.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Project,Domain,StatusProject } from '../../core/Model/project';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {
  projectForm!: FormGroup;
  userId: string = '4'; // Replace with the connected user's ID
  domainOptions!: string[];
  statusOptions!: string[];

  constructor(
    private fb: FormBuilder,
    private projectService: ProjectService,
    private toastr: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Initialize domain and status options
    this.domainOptions = Object.values(Domain).filter(val => typeof val === 'string');
    this.statusOptions = Object.values(StatusProject).filter(val => typeof val === 'string');

    // Initialize the form with controls and validators
    this.projectForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(15)]],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      nbDeveloper: ['', [Validators.required, Validators.min(5), Validators.max(8)]],
      objective: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(100)]],
      technology: ['', Validators.required],
      badget: ['', Validators.required],
      domain: ['', Validators.required],
      statusProject: ['', Validators.required]
    }, { validator: this.dateComparisonValidator });
  }

  dateComparisonValidator(formGroup: FormGroup) {
    const startDateControl = formGroup.get('startDate');
    const endDateControl = formGroup.get('endDate');

    if (startDateControl && endDateControl && startDateControl.value && endDateControl.value) {
      const startDate = new Date(startDateControl.value);
      const endDate = new Date(endDateControl.value);

      if (startDate >= endDate) {
        endDateControl.setErrors({ dateComparison: true });
      } else {
        endDateControl.setErrors(null);
      }
    }
  }

  addProject(): void {
    if (this.projectForm.valid) {
      const project = this.projectForm.value;

      this.projectService.addProjectAndAssignProjectToUser(project, this.userId).subscribe(
        response => {
          this.projectForm.reset();
          this.toastr.success('Project added successfully!', 'Success');

          setTimeout(() => {
            this.router.navigate(['/chefProjet/list-projet-chefp']);
          }, 1000);
        },
        error => {
          console.error('Error adding project:', error);
          this.toastr.error('Failed to add project. Please try again later.', 'Error');
        }
      );
    } else {
      Object.keys(this.projectForm.controls).forEach(field => {
        const control = this.projectForm.get(field);
        if (control && control.invalid) {
          control.markAsTouched({ onlySelf: true });
          this.toastr.error(this.getErrorMessage(field), 'Error');
        }
      });
    }
  }

  getErrorMessage(field: string): string {
    const control = this.projectForm.get(field);
    if (control && control.errors) {
      if (control.errors['required']) {
        return `${field} is required.`;
      } else if (control.errors['minlength']) {
        return `${field} must be at least ${control.errors['minlength'].requiredLength} characters long.`;
      } else if (control.errors['maxlength']) {
        return `${field} cannot be more than ${control.errors['maxlength'].requiredLength} characters long.`;
      } else if (control.errors['min']) {
        return `${field} must be at least ${control.errors['min'].min}.`;
      } else if (control.errors['max']) {
        return `${field} cannot be more than ${control.errors['max'].max}.`;
      } else if (control.errors['dateComparison']) {
        return 'End Date must be after Start Date.';
      }
    }
    return 'Invalid input.';
  }
}
