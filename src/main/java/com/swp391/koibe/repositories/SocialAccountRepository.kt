package com.swp391.koibe.repositories

import com.swp391.koibe.models.SocialAccount
import org.springframework.data.jpa.repository.JpaRepository

interface SocialAccountRepository : JpaRepository<SocialAccount, Long>
