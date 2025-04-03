import { Component, forwardRef, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorMessagePipe, TypeValidator, LanguagePatternValidator } from '@my/core';
import { LanguagesViewModelService } from './languagesViewModel.service';

@Component({
  selector: 'app-languages',
  templateUrl: './languages.component.html',
  styleUrls: ['./languages.component.css'],
  imports: [
    forwardRef(() => LanguagesAddComponent),
    forwardRef(() => LanguagesEditComponent),
    forwardRef(() => LanguagesViewComponent),
    forwardRef(() => LanguagesListComponent),
  ],
})
export class LanguagesComponent implements OnInit, OnDestroy {
  constructor(protected vm: LanguagesViewModelService) {}

  public get VM(): LanguagesViewModelService {
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
  selector: 'app-languages-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./languages.component.css'],
  imports: [RouterLink],
})
export class LanguagesListComponent implements OnInit, OnDestroy {
  constructor(protected vm: LanguagesViewModelService) {}

  public get VM(): LanguagesViewModelService {
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
  selector: 'app-languages-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./languages.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe, LanguagePatternValidator],
})
export class LanguagesAddComponent implements OnInit {
  languages: any[] = [];
  constructor(protected vm: LanguagesViewModelService) {}

  public get VM(): LanguagesViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.add();
  }
}

@Component({
  selector: 'app-languages-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./languages.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe, LanguagePatternValidator],
})
export class LanguagesEditComponent implements OnInit, OnDestroy {
  private obs$?: Subscription;
  languages: any[] = [];

  constructor(
    protected vm: LanguagesViewModelService,
    protected route: ActivatedRoute,
    protected router: Router,
  ) {}

  public get VM(): LanguagesViewModelService {
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
  selector: 'app-languages-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./languages.component.css'],
  imports: [DatePipe],
})
export class LanguagesViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(
    protected vm: LanguagesViewModelService,
    protected router: Router
  ) {}

  public get VM(): LanguagesViewModelService {
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
  LanguagesComponent,
  LanguagesListComponent,
  LanguagesAddComponent,
  LanguagesEditComponent,
  LanguagesViewComponent,
];
