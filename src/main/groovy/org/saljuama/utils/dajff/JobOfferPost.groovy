/*
 * Copyright (c) 2017 saljuama
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.saljuama.utils.dajff

import java.time.LocalTime

/**
 * @author saljuama
 */
class JobOfferPost {

    private String url
    private String topic
    private String content
    private String authorName
    private String authorUrl
    private Integer replies
    private LocalTime captureDate
    private String publishedAgo

    JobOfferPost(String url, String topic, String content, String authorName, String authorUrl, Integer replies, String publishedAgo) {
        this.url = url
        this.topic = topic
        this.content = content
        this.authorName = authorName
        this.authorUrl = authorUrl
        this.replies = replies
        this.publishedAgo = publishedAgo
        this.captureDate = LocalTime.now()
    }

    String getTopic() {
        return topic
    }

    LocalTime estimatedPublicationTime() {
        return null
    }

    Integer wordCount() {
        return 0
    }
}
