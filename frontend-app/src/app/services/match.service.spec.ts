import { MatchService } from './match.service';
import { ApiService } from './api.service';
import {TestBed} from "@angular/core/testing";

describe('MatchService', () => {

  let apiService: ApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    apiService = TestBed.inject(ApiService);
  });

  it('should create an instance', () => {
    expect(new MatchService(apiService)).toBeTruthy();
  });
});
