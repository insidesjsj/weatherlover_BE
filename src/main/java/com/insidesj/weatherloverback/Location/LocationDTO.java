package com.insidesj.weatherloverback.Location;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationDTO {
    String latitude;
    String longitude;
    String nx;
    String ny;
}
