import { Component, OnInit } from '@angular/core';
import { WhatSection } from '../../core/Model/what-section';
import { WhatSectionServiceService } from '../../core/Sevice/what-section-service.service';
import { WhySectionServiceService } from '../../core/Sevice/why-section-service.service';
import { WhySection } from '../../core/Model/why-section';
import { WhatIfSectionServiceService } from '../../core/Sevice/what-if-section-service.service';
import { WhatIfSection } from '../../core/Model/what-if-section';
import { WhoSection } from '../../core/Model/who-section';
import { WhoSectionService } from '../../core/Sevice/who-section.service';
import { Tutorial } from '../../core/Model/tutorial';
import { TutorialServiceService } from '../../core/Sevice/tutorial-service.service';
import { AvantagesService } from '../../core/Sevice/avantages.service';
import { Avantages } from '../../core/Model/avantages';
import { Router } from '@angular/router';
@Component({
  selector: 'ngx-scrum-pocker',
  templateUrl: './scrum-pocker.component.html',
  styleUrls: ['./scrum-pocker.component.scss']
})
export class ScrumPockerComponent implements OnInit {
  showSuccessMessage = false;
  showdeleteMessage= false;
  whatSections: WhatSection[] = [];
  newSection: WhatSection = { id: '', description: '' };
  isModalOpen: boolean = false;
  editMode: boolean = false;
  whySections: WhySection[] = [];
  newWhy: WhySection = { id: '', description: '' };
  isWhyModalOpen: boolean = false;
  editWhyMode: boolean = false;
  whatifSections: WhatIfSection[] = [];
  newwhatif: WhatIfSection = { id: '', description: '' };
  isWhatifModalOpen: boolean = false;
  editWhatifMode: boolean = false;
  whoSections: WhoSection[] = [];
  newWho: WhoSection = { id: '', description: '' };
  isWhoModalOpen: boolean = false;
  editWhoMode: boolean = false;
  Tutorial: Tutorial[] = [];
  newTutorial: Tutorial = { id: '', description: '' };
  isTutorialModalOpen: boolean = false;
  editTutorialMode: boolean = false;
  Avantages: Avantages[] = [];
  newAvantages: Avantages = { id: '', description: '' };
  isAvantagesModalOpen: boolean = false;
  editAvantagesMode: boolean = false;

  private idCounter: number = 1; // Initial ID counter

  constructor(
    private whatSectionService: WhatSectionServiceService,
    private whySectionService: WhySectionServiceService,
    private WhatIfSectionService :WhatIfSectionServiceService,
    private whoSectionSevice : WhoSectionService,
    private tutorialService : TutorialServiceService,
    private AvantagesService : AvantagesService,
    private router: Router
  ) { }
  

  ngOnInit(): void {
    this.loadWhatSections();
    this.loadWhySections();
    this.loadWhatifSections();
    this.loadWhoSections(); 
    this.loadtutorial();
    this.loadAvantages();

  }
  showSuccess() {
    this.showSuccessMessage = true;
    setTimeout(() => this.showSuccessMessage = false, 3000);
  }
  showdelete() {
    this.showdeleteMessage = true;
    setTimeout(() => this.showdeleteMessage = false, 3000);
  }
  toggleWhatModal() {
    this.isModalOpen = !this.isModalOpen;
    this.editMode = false;
  }

  toggleWhyModal() {
    this.isWhyModalOpen = !this.isWhyModalOpen;
    this.editWhyMode = false;
  }
  toggleWhoModal() {
    this.isWhoModalOpen = !this.isWhoModalOpen;
    this.editWhoMode = false;
  }
  toggletutorialModal() {
    this.isTutorialModalOpen = !this.isTutorialModalOpen;
    this.editTutorialMode = false;
  }
  
  toggleWhatifModal() {
    this.isWhatifModalOpen = !this.isWhatifModalOpen;
    this.editWhatifMode = false;
  }
  toggleAvantagesModal() {
    this.isAvantagesModalOpen = !this.isAvantagesModalOpen;
    this.editAvantagesMode = false;
  }
  
  addWhatSection() {
    // Generate a new ID using a counter
    this.newSection.id = `id_${this.idCounter++}`;

    // Call your service method to add the section
    this.whatSectionService.createSection(this.newSection).subscribe(() => {
      // Reload sections after adding
      this.loadWhatSections();
    });

    // Reset newSection for the next addition
    this.newSection = { id: '', description: '' };

    // Close the modal if needed
    this.isModalOpen = false;
    this.showSuccess(); 
  }

  loadWhatSections() {
    this.whatSectionService.getAllSections().subscribe(sections => {
      this.whatSections = sections;
    });
  }

  editWhatSection(section: WhatSection) {
    this.newSection = { ...section }; // Clone the section for editing
    this.editMode = true;
    this.isModalOpen = true;
  }

  onSubmitWhat() {
    if (this.editMode) {
      // Update the existing section
      this.whatSectionService.updateSection(this.newSection.id, this.newSection).subscribe(updatedSection => {
        const index = this.whatSections.findIndex(s => s.id === updatedSection.id);
        if (index !== -1) {
          this.whatSections[index] = updatedSection;
        }
        this.closeModal();
      });
    } else {
      // Create a new section
      this.addWhatSection();
    }
  }

  cancelWhat() {
    this.newSection.description = '';
    this.closeModal();
  }

  deleteWhatSection(sectionId: string) {
    this.whatSectionService.deleteSection(sectionId).subscribe(() => {
      this.whatSections = this.whatSections.filter(s => s.id !== sectionId);
      this.showdelete(); 
    });
  }

  addtutorial() {
    // Generate a new ID using a counter
    this.newTutorial.id = `id_${this.idCounter++}`;

    // Call your service method to add the section
    this.tutorialService.createTutorial(this.newTutorial).subscribe(() => {
    
      // Reload sections after adding
      this.loadtutorial();
     
    });

    // Reset newSection for the next addition
    this.newTutorial = { id: '', description: '' };

    // Close the modal if needed
    this.isTutorialModalOpen = false;
    this.showSuccess();
  }

  loadtutorial() {
    this.tutorialService.getAllTutorial().subscribe(sections => {
      this.Tutorial = sections;
    });
  }

  edittutorial(section: Tutorial) {
    this.newTutorial = { ...section }; // Clone the section for editing
    this.editTutorialMode = true;
    this.isTutorialModalOpen = true;
    
  }

  onSubmittutorial() {
    if (this.editTutorialMode) {
      // Update the existing section
      this.tutorialService.updateTutorial(this.newTutorial.id, this.newTutorial).subscribe(updatedtutorial => {
        const index = this.Tutorial.findIndex(s => s.id === updatedtutorial.id);
        if (index !== -1) {
          this.Tutorial[index] = updatedtutorial;
        }
        this.closeModal();
      });
    } else {
      // Create a new section
      this.addtutorial();
     
     
    }
  }

  canceltutorial() {
    this.newTutorial.description = '';
    this.closeModal();
  }

  deletetutorial(sectionId: string) {
    this.tutorialService.deleteTutorial(sectionId).subscribe(() => {
      this.Tutorial = this.Tutorial.filter(s => s.id !== sectionId);
      this.showdelete(); 
    });
  }

  

  toggleContent(sectionId: string) {
    const content = document.getElementById(sectionId + '-content');
    if (content.classList.contains('hidden')) {
      content.classList.remove('hidden');
    } else {
      content.classList.add('hidden');
    }
  }

  private closeModal() {
    this.isModalOpen = false;
    this.editMode = false;
    this.newSection = { id: '', description: '' }; // Reset the new section

    this.isWhyModalOpen = false;
    this.editWhyMode = false;
    this.newWhy = { id: '', description: '' }; // Reset the new why section
    this.isWhatifModalOpen = false;
    this.editWhatifMode = false;
    this.newwhatif = { id: '', description: '' }; // Reset the new why section
    this.isWhoModalOpen = false;
    this.editWhoMode = false;
    this.newWho = { id: '', description: '' }; 
    this.isTutorialModalOpen = false;
    this.editTutorialMode = false;
    this.newTutorial = { id: '', description: '' }; 
    this.isAvantagesModalOpen = false;
    this.editAvantagesMode = false;
    this.newAvantages = { id: '', description: '' }; 
  }

  addWhySection() {
    // Generate a new ID using a counter
    this.newWhy.id = `id_${this.idCounter++}`;

    // Call your service method to add the section
    this.whySectionService.createWhy(this.newWhy).subscribe(() => {
      // Reload sections after adding
      this.loadWhySections();
    });

    // Reset newWhy for the next addition
    this.newWhy = { id: '', description: '' };

    // Close the modal if needed
    this.isWhyModalOpen = false;
    this.showSuccess();
   

  }

  loadWhySections() {
    this.whySectionService.getAllWhy().subscribe(sections => {
      this.whySections = sections;
    });
  }

  editWhySection(section: WhySection) {
    this.newWhy = { ...section }; // Clone the section for editing
    this.editWhyMode = true;
    this.isWhyModalOpen = true;
  }

  onSubmitWhy() {
    if (this.editWhyMode) {
      // Update the existing section
      this.whySectionService.updateWhy(this.newWhy.id, this.newWhy).subscribe(updatedWhy => {
        const index = this.whySections.findIndex(s => s.id === updatedWhy.id);
        if (index !== -1) {
          this.whySections[index] = updatedWhy;
        }
        this.closeModal();
      });
    } else {
      // Create a new section
      this.addWhySection();
    }
  }

  cancelWhy() {
    this.newWhy.description = '';
    this.closeModal();
  }

  deleteWhySection(sectionId: string) {
    this.whySectionService.deleteWhy(sectionId).subscribe(() => {
      this.whySections = this.whySections.filter(s => s.id !== sectionId);
      this.showdelete(); 
    });
  }
  
  addWhatifSection() {
    // Generate a new ID using a counter
    this.newwhatif.id = `id_${this.idCounter++}`;

    // Call your service method to add the section
    this.WhatIfSectionService.createWhatif(this.newwhatif).subscribe(() => {
      // Reload sections after adding
      this.loadWhatifSections();
    });

    // Reset newWhatIf for the next addition
    this.newwhatif = { id: '', description: '' };

    // Close the modal if needed
    this.isWhatifModalOpen = false;
    this.showSuccess();
  }

  loadWhatifSections() {
    this.WhatIfSectionService.getAllWhatif().subscribe(sections => {
      this.whatifSections = sections;
    });
  }

  editWhatifSection(section: WhatIfSection) {
    this.newwhatif = { ...section }; // Clone the section for editing
    this.editWhatifMode = true;
    this.isWhatifModalOpen = true;
  }

  onSubmitWhatif() {
    if (this.editWhatifMode) {
      // Update the existing section
      this.WhatIfSectionService.updateWhatif(this.newwhatif.id, this.newwhatif).subscribe(updatedWhatif => {
        const index = this.whatifSections.findIndex(s => s.id === updatedWhatif.id);
        if (index !== -1) {
          this.whatifSections[index] = updatedWhatif;
        }
        this.closeModal();
      });
    } else {
      // Create a new section
      this.addWhatifSection();
    }
  }

  cancelWhatif() {
    this.newwhatif.description = '';
    this.closeModal();
  }

  deleteWhatifSection(sectionId: string) {
    this.WhatIfSectionService.deletewhatif(sectionId).subscribe(() => {
      this.whatifSections = this.whatifSections.filter(s => s.id !== sectionId);
      this.showdelete(); 
    });
  }
  addWhoSection() {
    // Generate a new ID using a counter
    this.newWho.id = `id_${this.idCounter++}`;

    // Call your service method to add the section
    this.whoSectionSevice.createWho(this.newWho).subscribe(() => {
      // Reload sections after adding
      this.loadWhoSections();
    });

    // Reset newWhy for the next addition
    this.newWho = { id: '', description: '' };

    // Close the modal if needed
    this.isWhoModalOpen = false;
    this.showSuccess();
  }

  loadWhoSections() {
    this.whoSectionSevice.getAllWho().subscribe(sections => {
      this.whoSections = sections;
    });
  }

  editWhoSection(section: WhoSection) {
    this.newWho = { ...section }; // Clone the section for editing
    this.editWhoMode = true;
    this.isWhoModalOpen = true;
  }

  onSubmitWho() {
    if (this.editWhoMode) {
      // Update the existing section
      this.whoSectionSevice.updateWho(this.newWho.id, this.newWho).subscribe(updatedWho => {
        const index = this.whoSections.findIndex(s => s.id === updatedWho.id);
        if (index !== -1) {
          this.whoSections[index] = updatedWho;
        }
        this.closeModal();
      });
    } else {
      // Create a new section
      this.addWhoSection();
    }
  }

  cancelWho() {
    this.newWhy.description = '';
    this.closeModal();
  }

  deleteWhoSection(sectionId: string) {
    this.whoSectionSevice.deleteWho(sectionId).subscribe(() => {
      this.whoSections = this.whoSections.filter(s => s.id !== sectionId);
      this.showdelete(); 
    });
  }
  
  addAvantages() {
    // Generate a new ID using a counter
    this.newAvantages.id = `id_${this.idCounter++}`;

    // Call your service method to add the section
    this.AvantagesService.createAvantages(this.newAvantages).subscribe(() => {
      // Reload sections after adding
      this.loadAvantages();
    });

    // Reset newWhy for the next addition
    this.newAvantages = { id: '', description: '' };

    // Close the modal if needed
    this.isAvantagesModalOpen = false;
    this.showSuccess();
  }

  
  loadAvantages() {
    this.AvantagesService.getAllAvantages().subscribe(sections => {
      this.Avantages = sections;
    });
  }

  editAvantages(section: Avantages) {
    this.newAvantages = { ...section }; // Clone the section for editing
    this.editAvantagesMode = true;
    this.isAvantagesModalOpen = true;
  }

  onSubmitAvantages() {
    if (this.editAvantagesMode) {
      // Update the existing section
      this.AvantagesService.updateAvantages(this.newAvantages.id, this.newAvantages).subscribe(updateAvantages => {
        const index = this.Avantages.findIndex(s => s.id === updateAvantages.id);
        if (index !== -1) {
          this.Avantages[index] = updateAvantages;
        }
        this.closeModal();
      });
    } else {
      // Create a new section
      this.addAvantages();
    }
  }

  cancelAvantages() {
    this.newAvantages.description = '';
    this.closeModal();
  }

  deleteAvantages(sectionId: string) {
    this.AvantagesService.deleteAvantages(sectionId).subscribe(() => {
      this.Avantages = this.Avantages.filter(s => s.id !== sectionId);
      this.showdelete(); 
    });
  }
  

  learnMore() {
    // Sélectionnez l'élément vers lequel vous voulez faire défiler
    const element = document.getElementById('what-title');
    if (element) {
      // Faites défiler la page vers l'élément cible
      element.scrollIntoView({ behavior: 'smooth' });
    }
  }
  
  goBack(): void {
    this.router.navigate(['/create-project']);
  }
}
