import {Component, Injectable, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Device} from "../../models/device";
import {MatchService} from "../../services/match.service";
import {MatSelect} from "@angular/material/select";
import {Tester} from "../../models/tester";
import {MatTableDataSource} from "@angular/material/table";


@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {


  public countries = new FormControl();
  public devices = new FormControl();
  public countryList: string[] = ['UK', 'PL'];
  public deviceList: Device[] = [{id: 1, description: 'iPhone'}, {id: 2, description: 'Nokia'}];
  private deviceListIds: number[] = [];
  private selectedCountries: string[] = [];
  private selectedDevices: number[] = [];

  displayedColumns: string[] = ['firstName', 'lastName', 'bugsTotal'];
  dataSource: MatTableDataSource<Tester> = new MatTableDataSource<Tester>();

  data: Tester[] = [];

  @ViewChild('countrySelect') countrySelect!: MatSelect;
  @ViewChild('deviceSelect') deviceSelect!: MatSelect;

  constructor(private matchService: MatchService) {
  }

  ngOnInit(): void {
    this.matchService.getDevices().subscribe(things => {
      console.log(things);
      this.deviceList = things;
      things.forEach(device => this.deviceListIds.push(device.id))
    }, error => {
      console.log("Error matchService.getDevices(): ", error)
    });

    this.matchService.getCountries().subscribe(things => {
      console.log(things);
      this.countryList = things;
    }, error => {
      console.log("Error matchService.getCountries(): ", error)
    })
  }

  async selectionChange(event: any) {
    if (!event) {
      this.selectedDevices = this.devices.value;
      this.selectedCountries = this.countries.value;
      await this.matchService.match(this.selectedCountries, this.selectedDevices).toPromise().then((testers: Tester[]) => {
        this.dataSource = new MatTableDataSource<Tester>(testers.sort((a, b) => 0 - (a.bugsTotal > b.bugsTotal ? 1 : -1)));
      }, error => {
        console.log("Error matchService.updateTable(): ", error)
      });
    }
  }

  selectAllCountries(ev1: any) {

    if (ev1._selected) {
      this.countries.setValue(this.countryList);
      ev1._selected = true;
    }
    if (ev1._selected == false) {
      this.countries.setValue([]);
    }

  }

  selectAllDevices(ev2: any) {

    if (ev2._selected) {
      this.devices.setValue(this.deviceListIds);
      ev2._selected = true;
    }
    if (ev2._selected == false) {
      this.devices.setValue([]);
    }

  }


}
