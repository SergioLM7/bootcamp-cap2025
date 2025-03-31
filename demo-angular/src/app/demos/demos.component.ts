import { Component, computed, OnDestroy, OnInit, signal } from '@angular/core';
import { NotificationService, NotificationType } from '../common-services';
import { Unsubscribable } from 'rxjs';

@Component({
  selector: 'app-demos',
  imports: [],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css',
})
export class DemosComponent implements OnInit, OnDestroy {
  private fecha = new Date("2025-03-31");

  public readonly name = signal<string>("Sergio");
  public readonly fontSize = signal<number>(20);
  public readonly list = signal([
    {id: 1, name: "Madrid"},
    {id: 2, name: "ciudad Real"},
    {id: 3, name: "barcelona"},
    {id: 4, name: "OVIEDO"},
  ]);

  public readonly idProvincia = signal<number>(2);

  public readonly result = signal<string>('');
  public readonly visible = signal<boolean>(true);
  public readonly invisible = computed<boolean>(() => !this.visible());

  public readonly style = signal({important: true, urgent: true, error: false});

  private suscriptor: Unsubscribable | undefined;

  constructor(public vm: NotificationService) {}

  public get Fecha(): string {
    return this.fecha.toISOString();
  }

  public set Fecha(value: string) {
    this.fecha = new Date(value);
  }

  greeting() {
    this.result.set(`Hola ${this.name()}`);
  }

  goodbye() {
    this.result.set(`Adiós ${this.name()}`);
  }

  say(something: string) {
    this.result.set(`Dice ${something}`);
  }

  change() {
    this.visible.update(current => !current);
    this.style.update(current => ({...current, important: !current.important }));
    this.style.update(current => ({...current, error: !current.error }));
  }

  add(provincia: string) {
    const id = this.list()[this.list().length + 1].id + 1;
    this.list.update(current => [...current, { id, name: provincia} ]);
    this.idProvincia.set(id);
  }

  calculate(a: number, b: number) {
    return a + b;
  }


  ngOnInit(): void {
    this.suscriptor = this.vm.Notificacion.subscribe((n) => {
      if (n.Type !== NotificationType.error) {
        return;
      }
      window.alert(`Suscripción: ${n.Message}`);
      this.vm.remove(this.vm.Listado.length - 1);
    });
  }

  ngOnDestroy(): void {
    if (this.suscriptor) {
      this.suscriptor.unsubscribe();
    }
  }
}
