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

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * @author saljuama
 */
class DeviantArtForum {

    private static String URL = "http://forum.deviantart.com"

    List<JobOfferPost> getJobOfferPosts() {
        List<JobOfferPost> jobOfferPosts = new ArrayList<>()
        Optional<String> uri = Optional.of("/jobs/offers/")
        while (uri.isPresent()) {
            Optional<Document> doc = HttpUtils.get(URL + uri.get())
            if (doc.isPresent()) {
                jobOfferPosts.addAll(getJobOffersFrom(doc.get()))
                uri = getNextPageLinkFrom(doc.get())
            }
        }
        println "Total posts: ${jobOfferPosts.size()}"
        return jobOfferPosts
    }

    private List<JobOfferPost> getJobOffersFrom(Document html) {
        Elements threads = html.select(".thread")
        List<JobOfferPost> jobOffers = new ArrayList<>()
        threads.each {
            if (it.hasAttr("id") && !it.hasClass("sticky")) {
                Elements topicColumn = it.select(".d-topic")
                Elements topicLink = topicColumn.select("a")
                String postUrl = topicLink.attr("href")
                String topic = topicLink.html()
                String content = "" // FIXME actually crawl the post and retrieve the content
                Elements authorColumn = it.select(".d-started-by")
                Elements authorLink = authorColumn.select(".username")
                String authorName = authorLink.html()
                String authorUrl = authorLink.attr("href")
                String publishedAgo = authorColumn.select('a').last().html()
                Integer replies = Integer.parseInt(it.select(".d-replies").html())
                JobOfferPost jobOffer = new JobOfferPost(postUrl, topic, content, authorName, authorUrl, replies, publishedAgo)
                jobOffers.add(jobOffer)
            }
        }
        return jobOffers
    }

    private Optional<String> getNextPageLinkFrom(Document html) {
        Elements nextButtonLink = html.select(".pagination > .pages > .next > a")
        String link = nextButtonLink.attr("href")
        return !link.trim().isEmpty() ? Optional.of(link) : Optional.empty()
    }
}