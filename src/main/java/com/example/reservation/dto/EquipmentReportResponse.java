package com.example.reservation.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Data
@ToString
public class EquipmentReportResponse {
    Set<EquipmentReport> equipmentReports = new HashSet<>();
    public void addEquipmentReport(EquipmentReport equipmentReport) {
        equipmentReports.add(equipmentReport);
    }
    @ToString
    @Data
    public static class EquipmentReport {
        private String equipmentName;
        private final Set<UsageStats> categoryUsageStats = new HashSet<>();
        private final Set<UsageStats> roomUsageStats = new HashSet<>();
        public void addCategoryUsageStats(UsageStats usageStats) {
            categoryUsageStats.add(usageStats);
        }
        public void addRoomUsageStats(UsageStats usageStats) {
            roomUsageStats.add(usageStats);
        }
        @ToString
        @Data
        public static class UsageStats {
            private String propertyName;
            private long numberOfUses;
            private long numberOfAllPossibleUses;
        }

    }
}
