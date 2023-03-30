#!/bin/bash

# -R:MinHeapSize=2m -R:MaxHeapSize=10m -R:MaxNewSize=1m
# -XX:+VerboseGC
# -XX:MaxDirectMemorySize (maximum size of direct buffer allocations)

# -H:G1HeapRegionSize (can only be specified at image build time) - the size of a G1 region.
# -XX:MaxRAMPercentage - the percentage of the physical memory size that is used as the maximum heap size if the maximum heap size is not specified otherwise.
# -XX:MaxGCPauseMillis - the goal for the maximum pause time.
# -XX:ParallelGCThreads - the maximum number of threads used for parallel work during garbage collection pauses.
# -XX:ConcGCThreads - the maximum number of threads used for concurrent work.
# -XX:InitiatingHeapOccupancyPercent - the Java heap occupancy threshold that triggers a marking cycle.
# -XX:G1HeapWastePercent - the allowed unreclaimed space in the collection set candidates. G1 stops the space-reclamation phase if the free space in the collection set candidates is lower than that

./target/poc-api-pix-java-1.0.0-SNAPSHOT-runner -XX:+PrintGC -Xms64m -Xmx128m