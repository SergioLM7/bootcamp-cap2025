import { Component, forwardRef, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorMessagePipe, TypeValidator, LanguagePatternValidator } from '@my/core';
import { CategoriesViewModelService } from './categoriesViewModel.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css'],
  imports: [
    forwardRef(() => CategoriesAddComponent),
    forwardRef(() => CategoriesEditComponent),
    forwardRef(() => CategoriesViewComponent),
    forwardRef(() => CategoriesListComponent),
  ],
})
export class CategoriesComponent implements OnInit, OnDestroy {
  constructor(protected vm: CategoriesViewModelService) {}

  public get VM(): CategoriesViewModelService {
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
  selector: 'app-categories-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./categories.component.css'],
  imports: [RouterLink],
})
export class CategoriesListComponent implements OnInit, OnDestroy {
  constructor(protected vm: CategoriesViewModelService) {}

  public get VM(): CategoriesViewModelService {
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
  selector: 'app-categories-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./categories.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe, LanguagePatternValidator],
})
export class CategoriesAddComponent implements OnInit {
  categories: any[] = [];
  constructor(protected vm: CategoriesViewModelService) {}

  public get VM(): CategoriesViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.add();
  }
}

@Component({
  selector: 'app-categories-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./categories.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe, LanguagePatternValidator],
})
export class CategoriesEditComponent implements OnInit, OnDestroy {
  private obs$?: Subscription;
  categories: any[] = [];

  constructor(
    protected vm: CategoriesViewModelService,
    protected route: ActivatedRoute,
    protected router: Router,
  ) {}

  public get VM(): CategoriesViewModelService {
    return this.vm;
  }

  ngOnInit(): void {
    this.obs$ = this.route.paramMap.subscribe((params: ParamMap) => {
      const id = parseInt(params?.get('id') ?? '');
      if (id) {
        this.vm.edit(id);
      } else {
        this.router.navigate(['/404.html']);
      }
    });
  }

  ngOnDestroy(): void {
    this.obs$!.unsubscribe();
  }
}

@Component({
  selector: 'app-categories-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./categories.component.css'],
  imports: [DatePipe],
})
export class CategoriesViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(
    protected vm: CategoriesViewModelService,
    protected router: Router
  ) {}

  public get VM(): CategoriesViewModelService {
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

export const LANGUAGES_COMPONENTS = [
  CategoriesComponent,
  CategoriesListComponent,
  CategoriesAddComponent,
  CategoriesEditComponent,
  CategoriesViewComponent,
];
