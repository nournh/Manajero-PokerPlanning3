import { TestBed } from '@angular/core/testing';

import { WhoSectionService } from './who-section.service';

describe('WhoSectionService', () => {
  let service: WhoSectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WhoSectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
