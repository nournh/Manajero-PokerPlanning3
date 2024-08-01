import { TestBed } from '@angular/core/testing';

import { WhatSectionServiceService } from './what-section-service.service';

describe('WhatSectionServiceService', () => {
  let service: WhatSectionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WhatSectionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
