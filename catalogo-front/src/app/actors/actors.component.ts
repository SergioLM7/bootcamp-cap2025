import { Component, forwardRef, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, ParamMap, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorMessagePipe, TypeValidator, UppercaseValidator } from '@my/core';
import { ActorsViewModelService } from './actorsViewModel.service';

@Component({
  selector: 'app-actors',
  templateUrl: './actors.component.html',
  styleUrls: ['./actors.component.css'],
  imports: [
    forwardRef(() => ActorsAddComponent),
    forwardRef(() => ActorsEditComponent),
    forwardRef(() => ActorsViewComponent),
    forwardRef(() => ActorsListComponent),
  ],
})
export class ActorsComponent implements OnInit, OnDestroy {
  constructor(protected vm: ActorsViewModelService) {}

  public get VM(): ActorsViewModelService {
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
  selector: 'app-actors-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./actors.component.css'],
  imports: [RouterLink],
})
export class ActorsListComponent implements OnInit, OnDestroy {
  constructor(protected vm: ActorsViewModelService) {}

  public get VM(): ActorsViewModelService {
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
  selector: 'app-actors-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./actors.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe, UppercaseValidator],
})
export class ActorsAddComponent implements OnInit {
  actors: any[] = [];
  constructor(protected vm: ActorsViewModelService) {}

  public get VM(): ActorsViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.add();
  }
}

@Component({
  selector: 'app-actors-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./actors.component.css'],
  imports: [FormsModule, TypeValidator, ErrorMessagePipe, UppercaseValidator],
})
export class ActorsEditComponent implements OnInit, OnDestroy {
  private obs$?: Subscription;
  actors: any[] = [];

  constructor(
    protected vm: ActorsViewModelService,
    protected route: ActivatedRoute,
    protected router: Router,
  ) {}

  public get VM(): ActorsViewModelService {
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
  selector: 'app-actors-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./actors.component.css'],
  imports: [DatePipe],
})
export class ActorsViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(
    protected vm: ActorsViewModelService,
    protected router: Router
  ) {}

  public get VM(): ActorsViewModelService {
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

export const ACTORS_COMPONENTS = [
  ActorsComponent,
  ActorsListComponent,
  ActorsAddComponent,
  ActorsEditComponent,
  ActorsViewComponent,
];
