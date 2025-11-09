import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'similarity-search',
    pathMatch: 'full'
  },
  {
    path: 'similarity-search',
    loadComponent: () =>
      import('./components/modules/similarity-search/similarity-search').then(m => m.SimilaritySearch)
  },
  {
    path: 'add-specification',
    loadComponent: () =>
      import('./components/modules/add-specification/add-specification').then(m => m.AddSpecification)
  }
];
