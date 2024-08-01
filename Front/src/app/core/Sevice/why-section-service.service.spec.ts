import { TestBed } from '@angular/core/testing';

import { WhySectionServiceService } from './why-section-service.service';

describe('WhySectionServiceService', () => {
  let service: WhySectionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WhySectionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
