package com.slothink.jgroups.cluster;



/**
 * Created by slothink on 2015-01-08.
 */
public interface JGroupsEcmClusterService {
    public void connect();
    public void clearCache();

    /**
     * 캐시된 정보에서 서버 정보들을 가져온다.
     * @return 서버 상태 정보
     */
    public ClusterInfo getCachedClusterInfo();

    /**
     * 캐시된 정보에서 살아있는 서버 정보들을 가져온다.
     * @return 서버 상태 정보
     */
    public ClusterInfo getCachedActiveClusterInfo();
}
