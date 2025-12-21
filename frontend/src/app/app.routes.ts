import { Routes } from '@angular/router';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'similarity-search',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./components/modules/login/login').then(m => m.Login)
  },
  {
    path: 'similarity-search',
    loadComponent: () =>
      import('./components/modules/similarity-search/similarity-search').then(m => m.SimilaritySearch),
    canActivate: [authGuard]
  },
  {
    path: 'add-specification',
    loadComponent: () =>
      import('./components/modules/add-specification/add-specification').then(m => m.AddSpecification),
    canActivate: [authGuard]
  }
];
