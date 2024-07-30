import { TestBed } from '@angular/core/testing';

import { WhatIfSectionServiceService } from './what-if-section-service.service';

describe('WhatIfSectionServiceService', () => {
  let service: WhatIfSectionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WhatIfSectionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
