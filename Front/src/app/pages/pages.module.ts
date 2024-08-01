import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { ThemeModule } from '../@theme/theme.module';
import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { ECommerceModule } from './e-commerce/e-commerce.module';
import { PagesRoutingModule } from './pages-routing.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import { ScrumPockerComponent } from './scrum-pocker/scrum-pocker.component';
import { FormsModule } from '@angular/forms';
import { DetailsProjetComponent } from './chef-projet/details-projet/details-projet.component';
import { DetailsSprintComponent } from './chef-projet/details-sprint/details-sprint.component';
import { ListProjetsComponent } from './chef-projet/list-projets/list-projets.component';
import { SprintsListComponent } from './chef-projet/sprints-list/sprints-list.component';
import { UpdateSprintComponent } from './chef-projet/update-sprint/update-sprint.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  imports: [
    FormsModule,
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    
    MiscellaneousModule,
    ReactiveFormsModule
  ],
  declarations: [
    PagesComponent,
    ScrumPockerComponent,
    DetailsProjetComponent,
    DetailsSprintComponent,
    ListProjetsComponent,
    SprintsListComponent,
    UpdateSprintComponent,
   
  ],
})
export class PagesModule {
}
