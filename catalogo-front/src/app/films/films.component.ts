import {
  Component,
  forwardRef,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';

import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { FilmsViewModelService } from './filmsViewModel.service';
import { ErrorMessagePipe, TypeValidator } from '@my/core';
import { CategoriesViewModelService } from '../categories/categoriesViewModel.service';
import { LanguagesViewModelService } from '../languages/languagesViewModel.service';
import { ActorsViewModelService } from '../actors/actorsViewModel.service';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-films',
  templateUrl: './films.component.html',
  styleUrls: ['./films.component.css'],
  imports: [
    forwardRef(() => FilmsAddComponent),
    forwardRef(() => FilmsEditComponent),
    forwardRef(() => FilmsViewComponent),
    forwardRef(() => FilmsListComponent),
  ],
})
export class FilmsComponent implements OnInit, OnDestroy {
  constructor(protected vm: FilmsViewModelService) {}

  public get VM(): FilmsViewModelService {
    return this.vm;
  }

  ngOnInit(): void {
    this.vm.list();
  }

  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-films-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./films.component.css'],
  imports: [RouterLink, PaginatorModule],
})
export class FilmsListComponent implements OnInit, OnDestroy {
  constructor(protected vm: FilmsViewModelService) {}

  public get VM(): FilmsViewModelService {
    return this.vm;
  }

  public get totalRecords(): number {
    return this.vm.TotalRecords;
  }

  public paginate(event: any): void {
    this.vm.paginate(event);
  }

  ngOnInit(): void {
    this.vm.listPaginated();
  }

  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-films-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./films.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe],
})
export class FilmsAddComponent implements OnInit {
  
  languages: any[] = [];
  categories: any[] = [];
  actors: any[] = [];
  selectedCategoryIds: number[] = [];
  selectedLanguageId: number = 0;
  selectedActorIds: number[] = [];

  constructor(
    protected vm: FilmsViewModelService,
    private languageVMService: LanguagesViewModelService,
    private categoriesVMService: CategoriesViewModelService,
    private actorsVMService: ActorsViewModelService
  ) {}

  public get VM(): FilmsViewModelService {
    return this.vm;
  }

  saveFilm() {
    throw new Error('Method not implemented.');
    }
    
  ngOnInit(): void {
    this.vm.add();

    this.actorsVMService.getAllActors().subscribe({
      next: (actors) => {
        this.actors = actors;
      },
      error: (err) => console.error('Error loading actors:', err),
    });

    this.languageVMService.getAllLanguages().subscribe({
      next: (languages) => {
        this.languages = languages;
      },
      error: (err) => console.error('Error loading languages:', err),
    });

    this.categoriesVMService.getAllCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (err) => console.error('Error loading categories:', err),
    });
  }

  createFilm() {
    const dataFilm = {
      description: this.vm.Element.description,
      length: this.vm.Element.length,
      rating: this.vm.Element.rating,
      releaseYear: this.vm.Element.releaseYear,
      rentalDuration: this.vm.Element.rentalDuration,
      rentalRate: this.vm.Element.rentalRate,
      replacementCost: this.vm.Element.replacementCost,
      title: this.vm.Element.title,
      languageId: this.selectedLanguageId,
      languageVO: null,
      actors: this.selectedActorIds,
      categories: this.selectedCategoryIds,
    };

    this.vm.Element = dataFilm;

    this.vm.send();
  }
}

@Component({
  selector: 'app-films-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./films.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe],
})
export class FilmsEditComponent implements OnInit, OnDestroy {

createFilm() {
throw new Error('Method not implemented.');
}
  private obs$?: Subscription;
  languages: any[] = [];
  categories: any[] = [];
  actors: any[] = [];
  selectedCategoryIds: number[] = [];
  selectedLanguageId: number = 0;
  selectedActorIds: number[] = [];
  filmId: number = 0;

  constructor(
    protected vm: FilmsViewModelService,
    protected route: ActivatedRoute,
    protected router: Router,
    private categoriesVMService: CategoriesViewModelService,
    private languageVMService: LanguagesViewModelService,
    private actorsVMService: ActorsViewModelService
  ) {}

  public get VM(): FilmsViewModelService {
    return this.vm;
  }

  ngOnInit(): void {
    this.obs$ = this.route.paramMap.subscribe((params: ParamMap) => {
      const id = parseInt(params?.get('id') ?? '');
      if (id) {
        this.filmId = id;

        this.actorsVMService.getAllActors().subscribe({
          next: (actors) => {
            this.actors = actors;
          },
          error: (err) => console.error('Error loading actors:', err),
        });

        this.languageVMService.getAllLanguages().subscribe({
          next: (languages) => {
            this.languages = languages;
          },
          error: (err) => console.error('Error loading languages:', err),
        });

        this.categoriesVMService.getAllCategories().subscribe({
          next: (categories) => {
            this.categories = categories;
          },
          error: (err) => console.error('Error loading categories:', err),
        });

        this.vm.getFilmById(id).subscribe({
          next: (film) => {
            this.onFilmLoaded(film);
          },
          error: (err) => console.error('Error loading film:', err),
        });

        this.vm.edit(id);
      } else {
        this.router.navigate(['/404.html']);
      }
    });
  }

  onFilmLoaded(film: any): void {
    if (film) {
      const language = this.languages.find((l) => l.idioma === film.language);
      this.selectedLanguageId = language ? language.id : 0;

      this.selectedCategoryIds = this.categories
        .filter((category) => film.categories.includes(category.category))
        .map((category) => category.id);

      this.selectedActorIds = this.actors
        .filter((actor) =>
          film.actors.includes(actor.firstName + ' ' + actor.lastName)
        )
        .map((actor) => actor.actorId);
    } else {
      console.error('No film data received');
    }
  }

  saveFilm() {
    const dataFilm = {
      filmId: this.filmId,
      description: this.vm.Element.description,
      length: this.vm.Element.length,
      rating: this.vm.Element.rating,
      releaseYear: this.vm.Element.releaseYear,
      rentalDuration: this.vm.Element.rentalDuration,
      rentalRate: this.vm.Element.rentalRate,
      replacementCost: this.vm.Element.replacementCost,
      title: this.vm.Element.title,
      languageId: this.selectedLanguageId,
      languageVO: null,
      actors: this.selectedActorIds,
      categories: this.selectedCategoryIds,
    };

    this.vm.Element = dataFilm;

    this.vm.send();
  }

  ngOnDestroy(): void {
    this.obs$!.unsubscribe();
  }
}

@Component({
  selector: 'app-films-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./films.component.css'],
  imports: [DatePipe],
})
export class FilmsViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(protected vm: FilmsViewModelService, protected router: Router) {}

  public get VM(): FilmsViewModelService {
    return this.vm;
  }

  ngOnChanges(_changes: SimpleChanges): void {
    if (this.id) {
      this.vm.view(+this.id);
    } else {
      this.router.navigate(['/404.html']);
    }
  }
}

export const FILMS_COMPONENTS = [
  FilmsComponent,
  FilmsListComponent,
  FilmsAddComponent,
  FilmsEditComponent,
  FilmsViewComponent,
];
