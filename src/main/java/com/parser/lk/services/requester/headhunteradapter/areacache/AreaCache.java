package com.parser.lk.services.requester.headhunteradapter.areacache;


import com.parser.lk.services.requester.headhunteradapter.dto.AreaResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AreaCache {

    private List<AreaCacheDTO> cache = new ArrayList<>();


    public int count() {
        return this.cache.size();
    }

    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    public AreaResponse getById(String id) {
        for (AreaCacheDTO dto : this.cache) {
            if (dto.getId().equals(id)) {
                AreaResponse areaResponse = new AreaResponse();
                areaResponse.setId(dto.getId());
                areaResponse.setName(dto.getName());
                return areaResponse;
            }
        }
        return null;
    }

    public void createCache(AreaResponse areaResponse) {
        AreaCacheDTO areaCacheDTO = new AreaCacheDTO();
        areaCacheDTO.setId(areaResponse.getId());
        areaCacheDTO.setName(areaCacheDTO.getName());
        this.cache.add(areaCacheDTO);
        for (AreaResponse response : areaResponse.getAreas()) {
            AreaCacheDTO dto = new AreaCacheDTO();
            dto.setId(response.getId());
            dto.setName(response.getName());
            this.cache.add(dto);
            for (AreaResponse response2 : response.getAreas()) {
                AreaCacheDTO dto2 = new AreaCacheDTO();
                dto2.setId(response2.getId());
                dto2.setName(response2.getName());
                this.cache.add(dto2);
            }
        }
    }

}
