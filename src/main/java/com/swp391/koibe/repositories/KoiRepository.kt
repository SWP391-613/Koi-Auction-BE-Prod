package com.swp391.koibe.repositories

import com.swp391.koibe.enums.EKoiStatus
import com.swp391.koibe.models.Koi
import com.swp391.koibe.responses.KoiInAuctionResponse
import com.swp391.koibe.responses.KoiResponse
import com.swp391.koibe.responses.pagination.KoiPaginationResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface KoiRepository : JpaRepository<Koi, Long> {
    fun existsByName(name: String): Boolean
    fun findByOwnerId(ownerId: Long, pageable: Pageable): Page<Koi>
    fun findByOwnerIdAndStatus(owner_id: Long, status: EKoiStatus, pageable: Pageable): Page<Koi>
    fun findByStatus(status: EKoiStatus, pageable: Pageable): Page<Koi>

    //SELECT koi data is display in existing auction
    //notice im want to retrieve the auction id at that last column to use in fe call
    @Query("SELECT new com.swp391.koibe.responses.KoiInAuctionResponse(k.id, k.name, k.sex, k.length, k.age, k.price, k.status, k.isDisplay, k.thumbnail, k.description, k.owner.id, k.category.id, ak.auction.id) " +
            "FROM Koi k INNER JOIN AuctionKoi ak ON k.id = ak.koi.id " +
            "WHERE k.status = 'VERIFIED' AND " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "k.name LIKE CONCAT('%', :keyword, '%') " +
            "OR k.description LIKE CONCAT('%', :keyword, '%') " +
            "OR k.sex LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(k.length AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(k.age AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(k.price AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR CAST(k.owner.id as string) LIKE CONCAT('%', :keyword, '%')" +
            "OR CAST(ak.bidMethod as string) LIKE CONCAT('%', :keyword, '%')" +
            "OR CAST(ak.bidStep as string) LIKE CONCAT('%', :keyword, '%'))")
    fun findByKeyword(@Param("keyword") keyword: String, pageable: Pageable): Page<KoiInAuctionResponse>

    @Query(
        "SELECT k FROM Koi k WHERE k.owner.id = :breederId " +
                "AND (k.name LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.sex as string) LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.length as string) LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.age as string) LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.price as string) LIKE CONCAT('%', :keyword, '%') " +
                "OR k.description LIKE CONCAT('%', :keyword, '%') " +
                "OR k.category.name LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.status as string) LIKE CONCAT('%', :keyword, '%'))"
    )
    fun findKoiByKeyword(@Param("keyword") keyword: String, @Param("breederId") breederId: Long, pageable: Pageable): Page<Koi>

    @Query(
        "SELECT k FROM Koi k WHERE k.status = 'UNVERIFIED' " +
                "AND (k.name LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.sex as string) LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.length as string) LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.age as string) LIKE CONCAT('%', :keyword, '%') " +
                "OR CAST(k.price as string) LIKE CONCAT('%', :keyword, '%') " +
                "OR k.description LIKE CONCAT('%', :keyword, '%') " +
                "OR k.category.name LIKE CONCAT('%', :keyword, '%'))"
    )
    fun findUnverifiedKoiByKeyword(keyword: String, pageable: Pageable): Page<Koi>

}
